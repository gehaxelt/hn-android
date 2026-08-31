# Builds the HN Android app (debug APK + unit tests) with podman/docker.
#
#   podman compose build
#   podman compose run --rm build
#
# The APK and the unit test results are copied into ./artifacts by the
# compose service.

FROM debian:bookworm AS builder

ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update && apt-get install -y --no-install-recommends \
    openjdk-17-jdk-headless wget unzip ca-certificates \
    && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

RUN mkdir -p $ANDROID_HOME/cmdline-tools
RUN wget -q https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -O /tmp/clt.zip \
    && unzip -q /tmp/clt.zip -d /tmp/clt \
    && mv /tmp/clt/cmdline-tools $ANDROID_HOME/cmdline-tools/latest \
    && rm -rf /tmp/clt /tmp/clt.zip

RUN yes | sdkmanager --licenses > /dev/null 2>&1 || true
RUN sdkmanager "platform-tools" "platforms;android-33" "build-tools;34.0.0"

WORKDIR /work
COPY . .
# AGP picks up the SDK from ANDROID_HOME, no local.properties needed
RUN ./gradlew --no-daemon :app:assembleDebug :app:testDebugUnitTest

# Slim image that only carries the build results
FROM debian:bookworm-slim
COPY --from=builder /work/app/build/outputs/apk/debug/app-debug-*.apk /artifacts/
COPY --from=builder /work/app/build/test-results /test-results/
WORKDIR /artifacts
