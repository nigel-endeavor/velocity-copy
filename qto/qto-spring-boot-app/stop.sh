#!/bin/bash
# Stop QTO Backend Server

echo "Stopping QTO Backend..."
pkill -f "qto-spring-boot-app.*bootRun" || echo "Server not running"
echo "Done"
