import { Button } from '@mui/material'
import Tooltip from '@mui/material/Tooltip'

export default function LoginButton() {
  // const isDev = process.env.NODE_ENV === 'development' ?? false

  // const loginURL = isDev
  //   ? `${process.env.NEXT_PUBLIC_CLIENT_HOST}/local-login`
  //   : `${process.env.NEXT_PUBLIC_SERVER_HOST}/oauth2/authorization/kakao`

  return (
    // <Link href={loginURL}>
    //   <img
    //     src="//k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
    //     width="180"
    //     alt="카카오 로그인 버튼"
    //   />
    // </Link>
    <Tooltip
      title="시연 준비로 인해 잠시 로그인이 불가능합니다. 양해 부탁드립니다."
      placement="left"
      arrow
    >
      <Button
        variant="contained"
        color="secondary"
        style={{ cursor: 'no-drop' }}
      >
        카카오 로그인
      </Button>
    </Tooltip>
  )
}
