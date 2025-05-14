import { Event } from './types'

export class ProductService {
  static changePrice(productId: string, newPrice: number): Event {
    return {
      topic: 'price-change',
      productId,
      data: { newPrice }
    }
  }
}
