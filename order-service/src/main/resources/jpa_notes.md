```
UNI-DIRECTIONAL MAPPING

```

```

OneToMany
Tables created: ORDERS, ORDER_DETAILS, ORDER_ORDER_DETAILS

public class Orders {
@OneToMany //owning side
private List<OrderDetails> orderDetails;
}

public class OrderDetails {
@ManyToOne
private Orders order_id;
}

```

```

BI-DIRECTIONAL MAPPING

```

```

OneToMany
Tables created: ORDERS, ORDER_DETAILS, ORDER_ORDER_DETAILS

public class Orders {
@OneToMany //owning side
private List<OrderDetails> orderDetails;
}

public class OrderDetails {
@ManyToOne
private Orders order_id;
}

```

```

```

```

