#!/bin/bash
# Stop QTO React Frontend
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

echo "✅ QTO Application Stopped!"
