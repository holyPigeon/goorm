import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { CategoriesName } from "./categories.type";


const initialState = CategoriesName.All;

export const categoriesSlice = createSlice({
    name: "category",
    initialState,
    reducers: {
        
    }
})


export default categoriesSlice.reducer;