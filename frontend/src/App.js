import React from 'react';
import { AuthProvider } from 'react-auth-kit';
import RoutesComponent from './Routes';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Container } from 'react-bootstrap'

const App = () => {
  return (
    <Container>
    <AuthProvider
      authName={"_auth"} authType={"cookie"}
      //refresh={refreshApi}
      cookieDomain={window.location.hostname}
      cookieSecure={window.location.protocol === "https:"}
    >
      <RoutesComponent />
      <ToastContainer />
    </AuthProvider>
    </Container>
  );
};

export default App;