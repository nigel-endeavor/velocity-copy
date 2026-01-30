import { ReactNode } from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';
import { useMsal } from '@azure/msal-react';

interface MainLayoutProps {
  children?: ReactNode;
}

export const MainLayout = ({ children }: MainLayoutProps) => {
  const { instance, accounts } = useMsal();
  const location = useLocation();

  const handleLogout = () => {
    instance.logoutRedirect({
      postLogoutRedirectUri: '/',
    });
  };

  const isActive = (path: string) => {
    return location.pathname === path;
  };

  return (
    <div className="flex flex-col min-h-screen bg-gray-50">
      {/* Header / Navigation */}
      <header className="bg-primary-600 text-white shadow-lg">
        <nav className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-8">
              <Link to="/" className="text-2xl font-bold hover:text-primary-100">
                QTO Application
              </Link>
              <div className="hidden md:flex space-x-4">
                <Link
                  to="/"
                  className={`px-3 py-2 rounded-lg transition-colors ${
                    isActive('/') ? 'bg-primary-700' : 'hover:bg-primary-700'
                  }`}
                >
                  Dashboard
                </Link>
                <Link
                  to="/services"
                  className={`px-3 py-2 rounded-lg transition-colors ${
                    isActive('/services') ? 'bg-primary-700' : 'hover:bg-primary-700'
                  }`}
                >
                  Services
                </Link>
              </div>
            </div>

            {accounts.length > 0 && (
              <div className="flex items-center space-x-4">
                <span className="text-sm">{accounts[0].username}</span>
                <button
                  onClick={handleLogout}
                  className="px-4 py-2 bg-white text-primary-600 rounded-lg hover:bg-gray-100 transition-colors font-medium"
                >
                  Logout
                </button>
              </div>
            )}
          </div>
        </nav>
      </header>

      {/* Main Content */}
      <main className="flex-1 container mx-auto px-4 py-8">
        {children || <Outlet />}
      </main>

      {/* Footer */}
      <footer className="bg-gray-800 text-white py-4">
        <div className="container mx-auto px-4 text-center text-sm">
          <p>&copy; 2026 QTO Application - Quantum Task Orchestrator</p>
        </div>
      </footer>
    </div>
  );
};

export default MainLayout;
