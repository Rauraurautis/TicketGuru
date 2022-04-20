// Placeholder-komponentti. Koko tän fileen tilalle jotain muuta,
// mikä sit tulee olemaankaan eka sivu loginin jälkeen

import React from 'react'
import { useAuthUser, useSignOut } from 'react-auth-kit';
import { useNavigate } from 'react-router-dom';
import { Button } from 'react-bootstrap'

const Secure = () => {
    const signOut = useSignOut();
    const authUser = useAuthUser();
    const navigate = useNavigate();

    return (
        <div>
            <h2>Hello {authUser().name}! Welcome to TicketGuru!</h2>
            <Button size="lg" onClick={() => navigate('/sales')}>Sell tickets</Button>
            {' '}
            <Button size="lg" onClick={() => signOut()}>Sign Out!</Button>
        </div>
    )
};

export default Secure;