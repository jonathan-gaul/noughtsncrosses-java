import { Injectable } from '@angular/core';
import { RxStomp } from '@stomp/rx-stomp';

@Injectable({
  providedIn: 'root'
})
export class StompService extends RxStomp {

  constructor() {
    super();
    this.configure({
      brokerURL: 'wss://' + window.location.host + '/api/websocket',

      connectHeaders: { },

      heartbeatIncoming: 0,
      heartbeatOutgoing: 20000,

      reconnectDelay: 5000,

      debug: (msg: string): void => {
        console.log(new Date(), msg);
      }
    });
    this.activate();
  }
}
