import { Event, Subscriber } from './types'

export class PubSubBroker {
  private subscribers: Subscriber[] = []

  subscribe(subscriber: Subscriber) {
    this.subscribers.push(subscriber)
  }

  publish(event: Event) {
    this.subscribers
      .filter(sub => sub.topic === event.topic && sub.filter(event))
      .forEach(sub => sub.notify(event))
  }
}
