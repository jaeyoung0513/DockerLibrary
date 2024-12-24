export default function errorDisplay(error) {
    if(error.code === "ECONNABORTED") {
        console.log("timeout");
    } else if (error.response) {
        console.log(error.response.data);
    }else if (error.request) {
        console.log(error.request);
    } else {
        console.log(error.message);
    }
    return (
        <div>
            <h1>Error</h1>
            <p>{error.message}</p>
        </div>
    )
}