# Messagestub

A stub application for receiving and sending messages for testing purposes.

Slightly biased and adapted for testing communication protocols used in the social domain in the Netherlands.

## Concepts

In the communication with other parties, a protocol is followed so that the parties communicate with messages following a
strict structure.

### Message

A message contains the actual information being transfered to another party. This message has a predeterimed format.

An example is a WMO301 message in the social domain, which is used to communicate an allocation of a certain service
or subsidiary to a certain client.

### Exchange

A message is exchanged through an exchange. This exchange has its own protocol which should be followed in order to send
and/or receive messages through this exchange.

An example is the StUF-GGK exchange (Standaard UitwisselingsFormat - Gemeenschappelijk GegevensKnooppunt), which is used
to exchange the WMO301 message to the suppliers of the service or subsidiary.

### ExchangeMessage

In order to send messages via the exchange, special envelopes or "wrappers" need to be used. This results in a new kind
of message, which envelopes the original message.

## Structure

TODO


