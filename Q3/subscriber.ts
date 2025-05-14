import { Subscriber } from './types'
import { NotificationService } from './notification-service'

export const createProductSubscriber = (
  productId: string,
  channels: Array<'email' | 'sms' | 'push'>
): Subscriber => ({
  topic: 'price-change',
  filter: event => event.productId === productId,
  notify: event => {
    if (channels.includes('email')) NotificationService.notifyByEmail(event)
    if (channels.includes('sms')) NotificationService.notifyBySMS(event)
    if (channels.includes('push')) NotificationService.notifyByPush(event)
  }
})
