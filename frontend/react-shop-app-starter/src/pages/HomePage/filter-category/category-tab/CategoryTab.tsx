import { FC } from 'react'
import { CategoriesName } from '../../../../store/categories/categories.type';

type CategoryTabProps = {
    text: string;
    categoryName: CategoriesName
}

const CategoryTab: FC<CategoryTabProps> = ({ text, categoryName }) => {
   

    return (
        <button
        >
            
        </button>
    )
}

export default CategoryTab