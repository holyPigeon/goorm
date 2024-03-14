import { FC } from 'react'
import styles from './CartItem.module.scss';
import { IProduct } from '../../../../store/products/products.type';

type CartItemProps = {
    item: IProduct;
}

const CartItem: FC<CartItemProps> = ({ item }) => {

    return (
        <div className={styles.cart_item}>

        </div>
    )
}

export default CartItem