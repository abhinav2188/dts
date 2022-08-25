
export function handleFormDataChange(event, setData) {
    let name = event.target.name;
    let value = event.target.value;
    setData(prevState => ({
        ...prevState,
        [name]: value
    }));
}
