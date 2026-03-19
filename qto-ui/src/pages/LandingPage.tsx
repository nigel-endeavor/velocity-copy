import { useEffect, useState } from 'react';
import { environment } from '../config/environment';

interface BackendStatus {
  connected: boolean;
  message: string;
  endpoint: string;
  responseTime?: number;
}

export const LandingPage = () => {
  const [status, setStatus] = useState<BackendStatus | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const checkBackend = async () => {
      const start = performance.now();
      try {
        setLoading(true);
        const res = await fetch(`${environment.appUrl}/services?page=0&size=1`);
        const elapsed = Math.round(performance.now() - start);
        if (res.ok) {
          setStatus({
            connected: true,
            message: `Backend responding (${res.status})`,
            endpoint: `${environment.appUrl}/services`,
            responseTime: elapsed,
          });
        } else {
          setStatus({
            connected: false,
            message: `Backend returned ${res.status} ${res.statusText}`,
            endpoint: `${environment.appUrl}/services`,
            responseTime: elapsed,
          });
        }
      } catch (err) {
        setStatus({
          connected: false,
          message: err instanceof Error ? err.message : 'Network request failed',
          endpoint: `${environment.appUrl}/services`,
        });
      } finally {
        setLoading(false);
      }
    };

    checkBackend();
  }, []);

  const handlePing = async () => {
    const start = performance.now();
    try {
      const res = await fetch(`${environment.appUrl}/services?page=0&size=1`);
      const elapsed = Math.round(performance.now() - start);
      alert(`Ping: ${res.status} in ${elapsed}ms`);
    } catch (err) {
      alert(`Ping failed: ${err instanceof Error ? err.message : 'Unknown error'}`);
    }
  };

  return (
    <div className="max-w-4xl mx-auto">
      <div className="bg-white rounded-lg shadow-xl p-8">
        {/* Header */}
        <div className="border-b pb-6 mb-6">
          <h1 className="text-4xl font-bold text-gray-900 mb-2">
            QTO Application
          </h1>
          <p className="text-xl text-gray-600">
            Quantum Task Orchestrator
          </p>
        </div>

        {/* Backend Status Section */}
        <div className="mb-8">
          <h2 className="text-2xl font-semibold text-gray-800 mb-4">
            Backend Status
          </h2>

          {loading ? (
            <div className="flex items-center justify-center py-8">
              <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
            </div>
          ) : status ? (
            <div className="bg-gray-50 rounded-lg p-6 space-y-3">
              <div className="flex items-start">
                <span className="font-semibold text-gray-700 w-32">Status:</span>
                <span className={`font-bold ${status.connected ? 'text-green-600' : 'text-red-600'}`}>
                  {status.connected ? 'CONNECTED' : 'DISCONNECTED'}
                </span>
              </div>
              <div className="flex items-start">
                <span className="font-semibold text-gray-700 w-32">Message:</span>
                <span className="text-gray-900">{status.message}</span>
              </div>
              <div className="flex items-start">
                <span className="font-semibold text-gray-700 w-32">Endpoint:</span>
                <span className="text-gray-900">{status.endpoint}</span>
              </div>
              {status.responseTime !== undefined && (
                <div className="flex items-start">
                  <span className="font-semibold text-gray-700 w-32">Latency:</span>
                  <span className="text-gray-900">{status.responseTime}ms</span>
                </div>
              )}
            </div>
          ) : null}
        </div>

        {/* Action Button */}
        <div className="flex justify-center">
          <button
            onClick={handlePing}
            className="px-6 py-3 bg-primary text-primary-foreground rounded-lg hover:bg-primary/90 transition-colors font-medium shadow-md hover:shadow-lg"
          >
            Test Ping
          </button>
        </div>

        {/* Info Cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mt-8">
          <div className="bg-blue-50 rounded-lg p-4 border border-blue-200">
            <h3 className="font-semibold text-blue-900 mb-2">React 19</h3>
            <p className="text-sm text-blue-700">Modern functional components with hooks</p>
          </div>
          <div className="bg-green-50 rounded-lg p-4 border border-green-200">
            <h3 className="font-semibold text-green-900 mb-2">Vite + Bun</h3>
            <p className="text-sm text-green-700">Lightning fast build and development</p>
          </div>
          <div className="bg-purple-50 rounded-lg p-4 border border-purple-200">
            <h3 className="font-semibold text-purple-900 mb-2">Tailwind CSS</h3>
            <p className="text-sm text-purple-700">Utility-first styling framework</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
