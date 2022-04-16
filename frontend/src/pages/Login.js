import axios from 'axios';
import React, { useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { useAuthUser, useIsAuthenticated, useSignIn } from 'react-auth-kit';
import { toast } from 'react-toastify';

const Login = () => {

    const [userDetails, setUserDetails] = useState({ username: "", password: "" });

    const isAuthenticated = useIsAuthenticated();
    const signIn = useSignIn();
    const navigate = useNavigate();

    const handleChange = (e) => {
        setUserDetails({ ...userDetails, [e.target.name]: e.target.value });
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/login', userDetails)
            .then(res => {
                if (res.status === 200) {
                    if (signIn({
                        token: res.data.access_token,
                        //tokenType: 'Bearer',
                        expiresIn: 10, //raukeamisaika vedetty hatusta, refreshtoken pitää implementoida kunnolla myöhemmin
                        authState: {name: userDetails.username}
                    })) {
                        localStorage.setItem('atoken', res.data.access_token);//purkkaratkaisu kun en muuta keksinyt
                        navigate('/secure');
                    } else {
                        toast.warn("There was some problem with logging in");
                    }
                } else {
                    toast.warn("There was some problem with logging in");
                }
            })
            .catch(e => {
                toast.warn("Wrong username or password!");
            });
    };

    if (isAuthenticated()) {
        return (<Navigate to={'/secure'} replace/>);
    } else {
        return (
            <div>
                <form onSubmit={handleSubmit}>
                    <label>
                        Username:
                        <input type="text" value={userDetails.username} name="username" onChange={handleChange} />
                    </label>
                    <label>
                        Password:
                        <input type="text" value={userDetails.password} name="password" onChange={handleChange} />
                    </label>
                    <input type="submit" value="Submit" />
                </form>
            </div>
        );
    }

};

export default Login;