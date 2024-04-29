import { style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const modalContainer = style ({
  zIndex: 101,
  position: 'fixed',
  top: 0,
  left: 0,
  width: '100%',
  height: '100%',
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  backgroundColor: 'rgba(0, 0, 0, 0.4)',
})

export const modalSubContainer = style ({
  backgroundColor:'white',
  borderRadius:'10px',
  width:'30%',
  height:'60%',
  overflowY:'auto',
  '::-webkit-scrollbar':{
    width: vars.space.tiny,
  },
  '::-webkit-scrollbar-thumb':{
    height: '5%',
    background: '#D3D3D3',
  },
})

export const topDivision = style ({
  display:'flex',
  justifyContent:'space-between'
})

export const topSubTitle = style ({
  margin:'0.5rem',
  marginLeft:'3%'
})

export const topSubXDiv = style ({
  display:'flex',
  alignItems:'center',
  marginRight:'3%'
})

export const button = style({
  width:"30%",
  borderRadius:'10px',
  backgroundColor:'#FF7E20',
  color:'white',
  textAlign:'center',
  boxShadow:vars.boxShadow.customOuter
})

export const toolInput = style ({
  width:'47%',
  height:'50%',
  padding:'0.1rem',
  paddingLeft:'0.6rem',
  marginTop:'20%',
  border:'none',
  borderRadius:'5px',
  borderBottom:'1px solid #F4F4F4',
  boxShadow:vars.boxShadow.customInner,
  backgroundColor:'#F4F4F4', outline: 'none'
})

export const urlInput = style ({
  width:'80%',
  height:'50%',
  padding:'0.1rem',
  paddingLeft:'0.6rem',
  marginTop:'3%',
  border:'none',
  borderRadius:'5px',
  borderBottom:'1px solid #F4F4F4',
  boxShadow:vars.boxShadow.customInner,
  backgroundColor:'#F4F4F4', outline: 'none'
})