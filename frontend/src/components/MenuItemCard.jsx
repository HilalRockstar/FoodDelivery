function MenuItemCard({ item, onAddToCart, disabled }) {

    return (
        <article className="menu-item-card">
            <div className="menu-item-art">{item.name?.split(' ').map((word) => word[0]).slice(0, 2).join('')}</div>
            <div className="menu-item-content">
            <div className="menu-item-top"><h3>{item.name}</h3><strong>₹{Number(item.price).toFixed(2)}</strong></div>
            <p>{item.description || 'A comforting favourite, prepared fresh to order.'}</p>
            <button className="button button-secondary" disabled={disabled} onClick={onAddToCart}>{item.available === false ? 'Unavailable' : 'Add to cart'} <span>+</span></button>
            </div>
        </article>
    )
}

export default MenuItemCard