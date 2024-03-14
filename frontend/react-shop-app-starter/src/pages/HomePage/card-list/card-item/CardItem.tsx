import { FC } from 'react'
import { IProduct } from '../../../../store/products/products.type';
import styles from './CardItem.module.scss';

type CardItemProps = {
  item: IProduct
}

const CardItem: FC<CardItemProps> = ({ item }) => {

  return (
    <li className={styles.card_item}>

    </li>
  )
}

export default CardItem