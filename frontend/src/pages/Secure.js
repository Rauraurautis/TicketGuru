// Placeholder-komponentti. Koko tän fileen tilalle jotain muuta,
// mikä sit tulee olemaankaan eka sivu loginin jälkeen

import React from 'react'
import { useAuthUser, useSignOut } from 'react-auth-kit';
import { useNavigate } from 'react-router-dom';

const Secure = () => {
    const signOut = useSignOut();
    const authUser = useAuthUser();
    const navigate = useNavigate();

    return (
        <div>
            <p>Hello {authUser().name}! Welcome to TicketGuru!</p>
            <button onClick={() => navigate('/sales')}>Sell tickets</button>
            <button onClick={() => signOut()}>Sign Out!</button>
        </div>
    )
};

export default Secure;