#!/bin/bash
# Stop QTO Backend and React Frontend
# Run this from the velocity directory

echo "🛑 Stopping QTO Application..."
echo ""

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# Stop Frontend
if [ -f "$SCRIPT_DIR/.qto-frontend.pid" ]; then
    FRONTEND_PID=$(cat "$SCRIPT_DIR/.qto-frontend.pid")
    echo "⚛️  Stopping Frontend (PID: $FRONTEND_PID)..."
    kill $FRONTEND_PID 2>/dev/null && echo "   Frontend stopped ✓" || echo "   Frontend not running"
    rm "$SCRIPT_DIR/.qto-frontend.pid"
else
    echo "⚛️  Stopping Frontend..."
    pkill -f "vite.*qto-ui-react" 2>/dev/null && echo "   Frontend stopped ✓" || echo "   Frontend not running"
fi
echo ""

# Stop Backend
if [ -f "$SCRIPT_DIR/.qto-backend.pid" ]; then
    BACKEND_PID=$(cat "$SCRIPT_DIR/.qto-backend.pid")
    echo "📦 Stopping Backend (PID: $BACKEND_PID)..."
    kill $BACKEND_PID 2>/dev/null && echo "   Backend stopped ✓" || echo "   Backend not running"
    rm "$SCRIPT_DIR/.qto-backend.pid"
else
    echo "📦 Stopping Backend..."
    pkill -f "qto-spring-boot-app.*bootRun" 2>/dev/null && echo "   Backend stopped ✓" || echo "   Backend not running"
fi
echo ""

echo "✅ QTO Application Stopped!"
