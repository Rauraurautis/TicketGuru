import React from 'react';
import { RequireAuth } from 'react-auth-kit'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Secure from './pages/Secure'
import Sales from './pages/Sales';

const RoutesComponent = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={'/'} element={<Home/>}/>
        <Route path={'/login' } element={<Login/>}/>
        <Route path={'/secure'} element={
          <RequireAuth loginPath={'/login'}>
            <Secure />
          </RequireAuth>
        }/>
        <Route path={'/sales'} element={
          <RequireAuth loginPath={'/login'}>
            <Sales/>
          </RequireAuth>}/>
      </Routes>
    </BrowserRouter>
  )
};

export default RoutesComponent;