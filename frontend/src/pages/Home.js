import React from 'react';
import { useNavigate } from 'react-router-dom';

const Home = () => {
    const navigate = useNavigate();

    return (
        <div>
            This is home
            <button onClick={()=> navigate('/login')}>Go to Login</button>
        </div>
    )
};

export default Home;