import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ListEvents from '../ListEvents'
import TicketSale from '../TicketSale'
import { Navigate, useNavigate } from 'react-router-dom';
import { Table, Form, Button } from 'react-bootstrap'
import { useLocation } from 'react-router-dom';

const Receipts = (props) => {

    const { state } = useLocation()
    console.log(state)
    const navigate = useNavigate()

    return (
        <div>
            <h3>Receipt</h3>
            <h5>Time Of Purchace: {state.dateOfSale}</h5>

            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>TicketCode</th>
                        <th>TicketType</th>
                        <th>NormalPrice</th>
                        <th>EventTitle</th>
                        <th>EventDescription</th>
                        <th>DateOfEvent</th>
                        <th>FinalPrice</th>
                    </tr>
                </thead>
                <tbody>
                    {state.tickets.map(ticket => (
                        <tr key={ticket.ticketId}>
                            <td>{ticket.ticketCode}</td>
                            <td>{ticket.ticketType.ticketTypeDescription}</td>
                            <td>{ticket.ticketType.price}</td>
                            <td>{ticket.ticketType.event.eventTitle}</td>
                            <td>{ticket.ticketType.event.eventDescription}</td>
                            <td>{ticket.ticketType.event.dateOfEvent}</td>
                            <td>{ticket.finalPrice}</td>
                        </tr>
                    ))}
                </tbody>
            </Table>
            <br />
            <Button size="lg" onClick={() => navigate('/sales')}>PrintAndGoToSales</Button>
            <Button size="lg" onClick={() => navigate('/sales')}>Sell more tickets</Button>
        </div>

    )
}

export default Receipts;