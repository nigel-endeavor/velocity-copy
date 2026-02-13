#!/bin/bash
# Start QTO React Frontend
# Run this from the velocity directory

echo "🚀 Starting QTO Application..."
echo ""

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# Start Frontend in background
echo "⚛️  Starting Frontend (React on port 7887)..."
cd "$SCRIPT_DIR/qto-ui"
bun --bun dev > /dev/null 2>&1 &
FRONTEND_PID=$!
echo "   Frontend PID: $FRONTEND_PID"
echo ""

# Save PID for stop script
echo "$FRONTEND_PID" > "$SCRIPT_DIR/.qto-frontend.pid"

echo "✅ QTO Application Started!"
echo ""
echo "📍 Access the application:"
echo "   Frontend: http://localhost:7887/qto-ops/"
echo ""
echo "🛑 To stop, run: ./STOP_ALL.sh"
echo ""
