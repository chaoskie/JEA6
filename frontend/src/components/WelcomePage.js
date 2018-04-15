import * as React from "react";
import { Card, CardActions, CardHeader, CardText } from 'material-ui/Card';

export const WelcomePage = () => (
    <Card className="welcomeCard">
        <CardText>
            <h1>See what’s happening in the world right now!</h1>
            <h2>Join Kwetter today!</h2>
        </CardText>
    </Card>
);