package com.folaukaveinga.jwt;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.folaukaveinga.model.User;
import com.folaukaveinga.utility.DateTimeUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	private Clock clock = DefaultClock.INSTANCE;

	@Value("${jwt.secret}")
	private String SECRET;

	@Value("${jwt.lifetime.duration}")
	private Long LIFETIME_DURATION;

	@Value("${jwt.subject}")
	private String SUBJECT;

	@Value("${jwt.creator}")
	private String CREATOR;

	/**
	 * claims.put("id", user.getId()); claims.put("email", user.getUsername());
	 * claims.put("roles", user.getRoles());
	 * 
	 * @param token
	 * @return
	 */
	public User getUserFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		String email = claims.get("email").toString();
		List<String> roles = (List<String>) claims.get("roles");
		int id = (int) claims.get("id");
		User user = new User(id, email, roles);
		return user;
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(clock.now());
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(User user) {
		final Date createdTime = clock.now();
		final Date expirationTime = getLifetimeExpiration(createdTime);
		JwtPayload jwtPayload = new JwtPayload(this.CREATOR, this.SUBJECT, expirationTime, user);
		return generateToken(jwtPayload);
	}

	public String generateToken(JwtPayload jwtPayload) {
		return Jwts.builder()
				.setPayload(jwtPayload.toJson())
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}

	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = getIssuedAtDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
				&& (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public String refreshToken(String token) {
		final Date createdDate = clock.now();
		final Date expirationDate = getLifetimeExpiration(createdDate);
		System.out.println("createdDate: " + createdDate);
		System.out.println("expirationDate: " + expirationDate);
		final Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);

		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

	public Boolean validateToken(String token, String uname, Date lastResetDate) {
		final String username = getUsernameFromToken(token);
		final Date created = getIssuedAtDateFromToken(token);
		// final Date expiration = getExpirationDateFromToken(token);
		return (username.equals(uname) && !isTokenExpired(token)
				&& !isCreatedBeforeLastPasswordReset(created, lastResetDate));
	}

	private Date getLifetimeExpiration(Date createdDate) {
		return new Date(createdDate.getTime() + DateTimeUtil.getHoursInMilliseconds(LIFETIME_DURATION));
	}
}