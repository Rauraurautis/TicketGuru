import React, { useState } from 'react';
import axios from 'axios';
import ListEvents from '../ListEvents'
import TicketSale from '../TicketSale'

const Sales = () => {
    const [event, setEvent] = useState('');

    return (
        <div>
            {event == '' ? <ListEvents setEvent={setEvent}/> : <TicketSale event={event}/>}
        </div>
    )
}

export default Sales;