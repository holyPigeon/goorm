import { createSlice } from "@reduxjs/toolkit";

const initialState = localStorage.getItem('user') ?
    JSON.parse(localStorage.getItem('user') || "") : { email: "", token: "", id: "" }


export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {

    }
})

export default userSlice.reducer;