def string_hash(s, m):
    p = 31  # a small prime
    hash_value = 0
    p_power = 1
    for char in s:
        hash_value = (hash_value + (ord(char) * p_power)) % m
        p_power = (p_power * p) % m
    return hash_value
