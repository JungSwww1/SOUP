import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'



export const category = style({
    width:' 80%',
    padding:'4px',
    borderRadius:'5px'
});
export const btnGroup = style({
    width:'10%',
    position: 'absolute',
    marginLeft: '2%',
    textAlign:'start',
    float: 'left',
    padding: '0 1% 0 1%',
    backgroundColor:'white',
    borderRadius: '5px',
    boxShadow: 'rgba(50, 50, 93, 0.25) 0px 13px 27px -5px, rgba(0, 0, 0, 0.3) 0px 8px 16px -8px',
    zIndex:3
})
export const elementGroup = style({
    display:'flex',
    flexDirection:'column',
    backgroundColor: 'white',
    zIndex:2,
})


globalStyle(`${elementGroup} > p:hover`,{
    background: 'whitesmoke',

});

export const select = style({
    backgroundColor:'yellowgreen',
    borderRadius:'4px',
    cursor:'pointer',

    margin: '0 0 10px 0',
})


export const manager = style({
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
})



globalStyle(`${manager}>p`, {
    margin: `0 ${vars.space.tiny} 0 0`
})

globalStyle(`${manager}>img`, {
    borderRadius: '50%',
    aspectRatio: 1
})
