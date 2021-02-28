import { env } from './env';

export const environment = {
  production: true,
  BACKEND_URL: env.BACKEND_URL,
  RAVE_API_KEY: env.RAVE_API_KEY,
  FRONTEND_URL: env.FRONTEND_URL,
};
