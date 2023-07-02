/** @type {import('tailwindcss').Config} */
const colors = require('tailwindcss/colors')

module.exports = {
  content: [
    './pages/**/*.{js,ts,jsx,tsx,mdx}',
    './components/**/*.{js,ts,jsx,tsx,mdx}',
    './app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      backgroundImage: {
        'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
        'gradient-conic':
          'conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))',
      },
    },
    colors: {
      transparent: 'transparent',
      current: 'currentColor',
      black: colors.black,
      teal: colors.teal,
      cyan: colors.cyan,
      indigo: colors.indigo,
      blue: colors.blue,
      sky: colors.sky,
      violet: colors.violet,
      fuchsia: colors.fuchsia,
      pink: colors.pink,
      rose: colors. rose,
      white: colors.white,
      gray: colors.gray,
      slate: colors.slate,
      zinc: colors.zinc,
      neutral: colors.neutral,
      stone: colors.stone,
      green: colors.green,
      emerald: colors.emerald,
      indigo: colors.indigo,
      yellow: colors.yellow,
      orange: colors.orange,
      amber: colors.amber,
      lime: colors.lime,
      emerald: colors.emerald,
      red: colors.red,
      htadarkgrey: '#333333',
      htadarkgreen: '#0B4619',
      htamediumgreen: '#116530',
      htabeige: '#E8E8CC',
      htasand: '#F2E2CB',
      htayellow: '#FFCC1D',
      htadarkbrown: '#433520',
      htadarkteal: '#025955',
      htadarktealhover: '#01403D',
      htamediumteal: '#00917C',
      htapinkbeige: '#FDE8CD'
    }
  },
  plugins: [],
}
