#!/bin/bash
# Start QTO Backend and React Frontend
# Run this from the velocity directory

echo "🚀 Starting QTO Application..."
echo ""

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# Start Backend in background
echo "📦 Starting Backend (Spring Boot on port 8080)..."
cd "$SCRIPT_DIR/qto/qto-spring-boot-app"
./gradlew bootRun --args='--spring.profiles.active=dev' > /dev/null 2>&1 &
BACKEND_PID=$!
echo "   Backend PID: $BACKEND_PID"
echo ""

# Wait a moment for backend to start
sleep 3

# Start Frontend in background
echo "⚛️  Starting Frontend (React on port 7887)..."
cd "$SCRIPT_DIR/qto-ui-react"
bun --bun dev > /dev/null 2>&1 &
FRONTEND_PID=$!
echo "   Frontend PID: $FRONTEND_PID"
echo ""

# Save PIDs for stop script
echo "$BACKEND_PID" > "$SCRIPT_DIR/.qto-backend.pid"
echo "$FRONTEND_PID" > "$SCRIPT_DIR/.qto-frontend.pid"

echo "✅ QTO Application Started!"
echo ""
echo "📍 Access the application:"
echo "   Frontend: http://localhost:7887/qto-ops/"
echo "   Backend:  http://localhost:8080/qto"
echo ""
echo "📝 View logs:"
echo "   Backend:  tail -f $SCRIPT_DIR/qto/qto-spring-boot-app/logs/application.log"
echo "   Frontend: (Check terminal or console)"
echo ""
echo "🛑 To stop both services, run: ./STOP_ALL.sh"
echo ""
echo "Press Ctrl+C to view this message again (services continue running in background)"
