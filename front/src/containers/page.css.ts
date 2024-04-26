import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({
  width: '100%',
  height: '100%',
})

export const content = style({
  padding: `calc( ${vars.space.xLarge} + ${vars.space.base} ) ${vars.space.base} 0 calc( ${vars.space.xLarge} + ${vars.space.base} )`,
})

export const projects = style({
  display: 'flex',
  flexWrap: 'wrap',

})

globalStyle(`${content} > p`, {
  fontWeight: 'bold',
  fontSize: vars.fontSize.t4
})