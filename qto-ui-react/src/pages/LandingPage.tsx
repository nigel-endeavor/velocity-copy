import { useEffect, useState } from 'react';
import { statusService, StatusResponse } from '../services/statusService';

export const LandingPage = () => {
  const [status, setStatus] = useState<StatusResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchStatus = async () => {
      try {
        setLoading(true);
        const data = await statusService.getStatus();
        setStatus(data);
        setError(null);
      } catch (err) {
        setError('Failed to fetch status');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchStatus();
  }, []);

  const handlePing = async () => {
    try {
      const pingData = await statusService.ping();
      alert(`Ping response: ${pingData.message}`);
    } catch (err) {
      alert('Ping failed');
      console.error(err);
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
              <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
            </div>
          ) : error ? (
            <div className="bg-red-50 border border-red-200 rounded-lg p-4">
              <p className="text-red-800">{error}</p>
            </div>
          ) : status ? (
            <div className="bg-gray-50 rounded-lg p-6 space-y-3">
              <div className="flex items-start">
                <span className="font-semibold text-gray-700 w-32">Application:</span>
                <span className="text-gray-900">{status.application}</span>
              </div>
              <div className="flex items-start">
                <span className="font-semibold text-gray-700 w-32">Version:</span>
                <span className="text-gray-900">{status.version}</span>
              </div>
              <div className="flex items-start">
                <span className="font-semibold text-gray-700 w-32">Status:</span>
                <span
                  className={`font-bold ${
                    status.status === 'OPERATIONAL'
                      ? 'text-green-600'
                      : 'text-red-600'
                  }`}
                >
                  {status.status}
                </span>
              </div>
              <div className="flex items-start">
                <span className="font-semibold text-gray-700 w-32">Phase:</span>
                <span className="text-gray-900">{status.phase}</span>
              </div>
              <div className="flex items-start">
                <span className="font-semibold text-gray-700 w-32">Message:</span>
                <span className="text-gray-900">{status.message}</span>
              </div>
            </div>
          ) : null}
        </div>

        {/* Action Button */}
        <div className="flex justify-center">
          <button
            onClick={handlePing}
            className="px-6 py-3 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition-colors font-medium shadow-md hover:shadow-lg"
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
