import { Event } from './types'

export class NotificationService {
  static notifyByEmail(event: Event) {
    console.log(`[email] produto ${event.productId}:`, event.data)
  }

  static notifyBySMS(event: Event) {
    console.log(`[sms] produto ${event.productId}:`, event.data)
  }

  static notifyByPush(event: Event) {
    console.log(`[push] produto ${event.productId}:`, event.data)
  }
}
