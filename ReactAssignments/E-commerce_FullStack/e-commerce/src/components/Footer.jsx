import React from "react";
import { Container } from "react-bootstrap";

function Footer() {
    return (
        <footer className="footer bg-dark text-center text-muted py-3 mt-5">
            <Container>
                <p className="mb-0 custom-text-color">
                    © {new Date().getFullYear()} E-Commerce | All Rights Reserved
                </p>
            </Container>
        </footer>
    );
}

export default Footer;
