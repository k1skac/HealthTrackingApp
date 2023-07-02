import Image from 'next/image'
import mypic from '../asset/hta_logo_v3.png'

const Logo = (props) => {
  return (
    <Image
      src={mypic}
      alt="HtaLogo"
      className='object-scale-down h-36 w-36'
      priority={true}
    />
  )
}
export default Logo;