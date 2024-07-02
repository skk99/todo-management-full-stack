import React from 'react'

const FooterComponent = () => {
    const year = new Date().getFullYear();
    return (
        <div>
            <footer className='footer'>
                <p>ⓒ {year} All Rights Reserved by Shankar</p>
            </footer>
        </div>
    )
}

export default FooterComponent