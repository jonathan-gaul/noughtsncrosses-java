# Use an appropriate base image
FROM mcr.microsoft.com/devcontainers/java:1-17-bookworm

# Install necessary tools and dependencies (e.g., Maven)
RUN apt-get update && apt-get install -y maven && apt-get install -y nodejs npm

# Switch to root user to adjust permissions
USER root

RUN mkdir -p /workspaces/noughtsncrosses-java
RUN chown -R vscode:vscode /workspaces/noughtsncrosses-java
RUN chmod -R 755 /workspaces/noughtsncrosses-java

# Switch back to vscode user
USER vscode