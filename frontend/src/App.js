import React from 'react';
import { AuthProvider } from 'react-auth-kit';
import RoutesComponent from './Routes';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const App = () => {
  return (
    <AuthProvider
      authName={"_auth"} authType={"cookie"}
      //refresh={refreshApi}
      cookieDomain={window.location.hostname}
      cookieSecure={window.location.protocol === "https:"}
    >
      <RoutesComponent />
      <ToastContainer />
    </AuthProvider>
  );
};

export default App;