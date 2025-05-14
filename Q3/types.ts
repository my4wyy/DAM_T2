export type Topic = 'price-change' | 'stock-update' | 'promotion'

export interface Event {
  topic: Topic
  productId: string
  data: any
}

export interface Subscriber {
  topic: Topic
  filter: (event: Event) => boolean
  notify: (event: Event) => void
}
