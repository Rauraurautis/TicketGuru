import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import ListEvent from '../ListEvents'

const Sales = () => {
    const [id, setId] = useState('');

    return (
        <div>
            <ListEvent setId={setId}/>
            <p>{id}</p>
        </div>
    )
}

export default Sales;