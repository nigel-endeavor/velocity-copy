import { MsalProvider } from '@azure/msal-react';
import { PublicClientApplication } from '@azure/msal-browser';
import { RouterProvider } from 'react-router-dom';
import { Provider } from 'react-redux';
import { msalConfig } from './config/msal.config';
import { store } from './store';
import { router } from './router';

const msalInstance = new PublicClientApplication(msalConfig);

function App() {
  return (
    <MsalProvider instance={msalInstance}>
      <Provider store={store}>
        <RouterProvider router={router} />
      </Provider>
    </MsalProvider>
  );
}

export default App;
