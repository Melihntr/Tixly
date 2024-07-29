import { createSlice } from '@reduxjs/toolkit';

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    authKey: null,
    username: '',
    status: 'idle', // Make sure this exists
    error: null
  },
  reducers: {
    loginSuccess: (state, action) => {
      state.authKey = action.payload.authKey;
      state.username = action.payload.username;
      state.status = 'succeeded';
      state.error = null;
    },
    loginFailure: (state, action) => {
      state.status = 'failed';
      state.error = action.payload;
    },
    logout: (state) => {
      state.authKey = null;
      state.username = '';
      state.status = 'idle';
      state.error = null;
    }
  }
});

export const { loginSuccess, loginFailure, logout } = authSlice.actions;
export default authSlice.reducer;
