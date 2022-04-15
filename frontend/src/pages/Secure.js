// Placeholder-komponentti. Koko tän fileen tilalle jotain muuta,
// mikä sit tulee olemaankaan eka sivu loginin jälkeen

import React from 'react'
import { useAuthUser, useSignOut } from 'react-auth-kit'

const Secure = () => {
    const signOut = useSignOut();
    const authUser = useAuthUser();

    return (
        <div>
            <p>Hello {authUser().name}, this is a placeholder page. Actual first page after login will be here</p>
            <button onClick={() => signOut()}>Sign Out!</button>
        </div>
    )
};

export default Secure;