module.exports = {
  parser: '@typescript-eslint/parser',
  parserOptions: {
    project: './tsconfig.json',
    ecmaVersion: 2020,
    sourceType: 'module',
  },
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'prettier' // if you're using Prettier
  ],
  plugins: ['@typescript-eslint'],
  rules: {
    // Add or override rules here
    '@typescript-eslint/no-unused-vars': ['error'],
  },
  env: {
    node: true,
    es2020: true,
  },
};
