import { PubSubBroker } from './pubsub'
import { createProductSubscriber } from './subscriber'
import { ProductService } from './publisher'

const broker = new PubSubBroker()

// usuário quer notificações por email e push no produto 123
broker.subscribe(createProductSubscriber('123', ['email', 'push']))

// simula mudança de preço
const priceChangeEvent = ProductService.changePrice('123', 79.90)
broker.publish(priceChangeEvent)
