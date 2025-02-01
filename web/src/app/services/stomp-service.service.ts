import { Injectable } from '@angular/core';
import { RxStomp } from '@stomp/rx-stomp';

@Injectable({
  providedIn: 'root'
})
export class StompService extends RxStomp {

  constructor() {
    super();
    this.configure({
      brokerURL: 'ws://' + window.location.host + '/api/websocket', // Change to ws:// for testing

      connectHeaders: { },

      heartbeatIncoming: 0,
      heartbeatOutgoing: 20000,

      reconnectDelay: 5000,

      debug: (msg: string): void => {
        console.log(new Date(), msg);
      }
    });

    this.activate();

    // Add error handling
    this.stompClient.onWebSocketError = (error) => {
      console.error('WebSocket error: ', error);
    };

    this.stompClient.onStompError = (frame) => {
      console.error('STOMP error: ', frame);
    };
  }
}
