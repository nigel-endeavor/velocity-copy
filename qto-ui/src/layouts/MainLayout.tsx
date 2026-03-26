import { ReactNode } from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';

interface MainLayoutProps {
  children?: ReactNode;
}

export const MainLayout = ({ children }: MainLayoutProps) => {
  const location = useLocation();

  const isActive = (path: string) => {
    return location.pathname === path;
  };

  return (
    <div className="flex flex-col min-h-screen bg-background">
      {/* Header / Navigation */}
      <header className="bg-primary text-primary-foreground shadow-lg">
        <nav className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-8">
              <Link to="/" className="text-2xl font-bold hover:opacity-80">
                QTO Application
              </Link>
              <div className="hidden md:flex space-x-4">
                <Link
                  to="/"
                  className={`px-3 py-2 rounded-lg transition-colors ${
                    isActive('/') ? 'bg-black/20' : 'hover:bg-black/10'
                  }`}
                >
                  Home
                </Link>
                <Link
                  to="/dashboard"
                  className={`px-3 py-2 rounded-lg transition-colors ${
                    isActive('/dashboard') ? 'bg-black/20' : 'hover:bg-black/10'
                  }`}
                >
                  Dashboard
                </Link>
                <Link
                  to="/orders"
                  className={`px-3 py-2 rounded-lg transition-colors ${
                    isActive('/orders') ? 'bg-black/20' : 'hover:bg-black/10'
                  }`}
                >
                  Orders
                </Link>
                <Link
                  to="/quotes"
                  className={`px-3 py-2 rounded-lg transition-colors ${
                    isActive('/quotes') ? 'bg-black/20' : 'hover:bg-black/10'
                  }`}
                >
                  Quotes
                </Link>
                <Link
                  to="/services"
                  className={`px-3 py-2 rounded-lg transition-colors ${
                    isActive('/services') ? 'bg-black/20' : 'hover:bg-black/10'
                  }`}
                >
                  Services
                </Link>
                <Link
                  to="/inventory"
                  className={`px-3 py-2 rounded-lg transition-colors ${
                    isActive('/inventory') ? 'bg-black/20' : 'hover:bg-black/10'
                  }`}
                >
                  Inventory
                </Link>
                <Link
                  to="/customers"
                  className={`px-3 py-2 rounded-lg transition-colors ${
                    isActive('/customers') ? 'bg-black/20' : 'hover:bg-black/10'
                  }`}
                >
                  Customers
                </Link>
                <Link
                  to="/invoicing"
                  className={`px-3 py-2 rounded-lg transition-colors ${
                    isActive('/invoicing') ? 'bg-black/20' : 'hover:bg-black/10'
                  }`}
                >
                  Invoicing
                </Link>
                <Link
                  to="/expenses"
                  className={`px-3 py-2 rounded-lg transition-colors ${
                    isActive('/expenses') ? 'bg-black/20' : 'hover:bg-black/10'
                  }`}
                >
                  Expenses
                </Link>
              </div>
            </div>
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
